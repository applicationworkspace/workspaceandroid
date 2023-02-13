package com.workspaceandroid.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.max
import com.workspaceandroid.R
import com.workspaceandroid.ui.theme.icon_size_24
import com.workspaceandroid.ui.theme.offset_4
import com.workspaceandroid.ui.theme.radius_8

@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    fullText: String,
    hyperLinks: Map<String, String>,
    textStyle: TextStyle = TextStyle.Default,
    linkTextColor: Color = Color.Blue,
    linkTextFontWeight: FontWeight = FontWeight.Normal,
    linkTextDecoration: TextDecoration = TextDecoration.None,
    fontSize: TextUnit = TextUnit.Unspecified,
    clickListener: (() -> Unit)? = null
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)

        for((key, value) in hyperLinks){

            val startIndex = fullText.indexOf(key)
            val endIndex = startIndex + key.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = value,
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = textStyle,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    clickListener?.let {
                        clickListener.invoke()
                    } ?: run {
                        uriHandler.openUri(stringAnnotation.item)
                    }
                }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String? = null,
    onInputChanged: (String) -> Unit,
    inputType: KeyboardType,
    placeholderText: String,
    isError: Boolean = false,
    maxLines: Int = 1
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        label?.let {
            Text(
                text = label,
                fontWeight = FontWeight.SemiBold
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(top = offset_4)
                .background(color = Color.White)
                .fillMaxWidth(),
            value = value,
            isError = isError,
            maxLines = maxLines,
            singleLine = true,
            onValueChange = { onInputChanged(it) },
            keyboardOptions = KeyboardOptions(keyboardType = inputType),
            placeholder = { Text(text = placeholderText) },
            shape = RoundedCornerShape(radius_8),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
    }
}//TODO surface instead Text with params


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String? = null,
    onInputChanged: (String) -> Unit,
    placeholderText: String,
    isError: Boolean = false,
    maxLines: Int = 1
) {
    var passwordVisible: Boolean by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        label?.let {
            Text(
                text = label,
                fontWeight = FontWeight.SemiBold
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(top = offset_4)
                .background(color = Color.White)
                .fillMaxWidth(),
            value = value,
            isError = isError,
            maxLines = maxLines,
            singleLine = true,
            onValueChange = { onInputChanged(it) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val iconPainter = if (passwordVisible) painterResource(R.drawable.ic_visibility_on)
                else painterResource(R.drawable.ic_visibility_off)

                val description = if (passwordVisible) stringResource(R.string.auth_password_show)
                else stringResource(R.string.auth_password_hide)

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = iconPainter,
                        contentDescription = description,
                        modifier = Modifier.size(icon_size_24)
                    )
                }
            },
            placeholder = { Text(text = placeholderText) },
            shape = RoundedCornerShape(radius_8),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )
    }
}
