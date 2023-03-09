pipeline {
  agent {
    label 'built-in'
  }
  options {
    // Stop the build early in case of compile or test failures
    skipStagesAfterUnstable()
  }
  stages {
    stage('Compile') {
      steps {
        // Compile the app and its dependencies
        sh './gradlew compileDebugSources'
      }
    }
    stage('Unit test') {
      steps {
        // Compile and run the unit tests for the app and its dependencies
        sh './gradlew testDebugUnitTest'
        // Analyse the test results and update the build result as appropriate
        junit '**/TEST-*.xml'
      }
    }
    stage('Build debug APK') {
      steps {
        // Finish building and packaging the APK
        sh './gradlew assembleDebug'
        archiveArtifacts '**/*.apk'
      }
    }
    stage('Static analysis') {
      steps {
        // Run Lint and analyse the results
        sh './gradlew lintDebug'
        androidLintParser pattern: '**/lint-results-*.xml'
      }
    }

    stage('Deploy distribution') {
       when {
          // Only execute this stage when building from the `develop` branch
          branch 'develop'
       }
       steps {
         echo "Uploading to app distribution"
         withCredentials([string(credentialsId: 'firebase-android-app-id', variable: 'FIREBASE_ANDROID_APP_ID'), string(credentialsId: 'firebase-distribution-token', variable: 'FIREBASE_DISTRIBUTION_TOKEN')]) {
            sh 'firebase appdistribution:distribute $WORKSPACE/app/build/outputs/apk/debug/app-debug.apk --app $FIREBASE_ANDROID_APP_ID --token $FIREBASE_DISTRIBUTION_TOKEN --release-notes "notes here jenkins" --groups "app-testers"'
         }
       }
       post {
          success {
            // Notify if the upload succeeded
            withCredentials([string(credentialsId: 'jenkins-slack-token', variable: 'SLACK_TOKEN')]) {
                slackSend( channel: "#pipeline_process", token: "${SLACK_TOKEN}", color: "#00ff00", message: "Distribution" )
            }
          }
        }
      }

  }

  post {
    failure {
        withCredentials([string(credentialsId: 'jenkins-slack-token', variable: 'SLACK_TOKEN')]) {
            slackSend( channel: "#pipeline_process", token: "${SLACK_TOKEN}", color: "#FF0000", message: "${custom_msg_error()}")
        }
    }
  }

}

def custom_msg_error()
    {
        def JENKINS_URL= "localhost:8080"
        def JOB_NAME = env.JOB_NAME
        def BUILD_ID = env.BUILD_ID
        def JENKINS_LOG = "Job [${env.JOB_NAME}] Logs path: ${JENKINS_URL}/job/${JOB_NAME}/${BUILD_ID}"
        return JENKINS_LOG
}

// def custom_msg_deploy_success()
// {
//     def MESSAGE_BODY = "              {
//                                         "blocks": [
//                                           {
//                                             "type": "header",
//                                             "text": {
//                                               "type": "plain_text",
//                                               "text": "CI/CD workflow (Jenkins)"
//                                             }
//                                           },
//                                           {
//                                             "type": "section",
//                                             "fields": [
//                                               {
//                                                 "type": "mrkdwn",
//                                                 "text": "*Type:*\nNew QA build"
//                                               }
//                                             ]
//                                           },
//                                           {
//                                             "type": "divider"
//                                           },
//                                           {
//                                             "type": "section",
//                                             "text": {
//                                               "type": "mrkdwn",
//                                               "text": "*Details:*\n$ Git message here"
//                                             }
//                                           }
//                                         ]
//                                       }
//                                       "
// }