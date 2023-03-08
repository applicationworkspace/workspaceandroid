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
//     stage('Unit test') {
//       steps {
//         // Compile and run the unit tests for the app and its dependencies
//         sh './gradlew testDebugUnitTest'
//         // Analyse the test results and update the build result as appropriate
//         junit '**/TEST-*.xml'
//       }
//     }
//     stage('Build debug APK') {
//       steps {
//         // Finish building and packaging the APK
//         sh './gradlew assembleDebug'
//         // Archive the APKs so that they can be downloaded from Jenkins
//         archiveArtifacts '**/*.apk'
//       }
//     }
//     stage('Static analysis') {
//       steps {
//         // Run Lint and analyse the results
//         sh './gradlew lintDebug'
//         androidLintParser pattern: '**/lint-results-*.xml'
//       }
//     }

    stage('Deploy distribution') {
       when {
          // Only execute this stage when building from the `develop` branch
          branch 'develop'
       }
       steps {
         echo "Uploading to app distribution"
         sh 'firebase appdistribution:distribute app/build/outputs/apk/debug/app-debug.apk --app $FIREBASE_ANDROID_APP_ID --release-notes "$GIT_COMMIT_MESSAGE" --groups "app-testers"'
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
      // Notify team of the failure
      withCredentials([string(credentialsId: 'jenkins-slack-token', variable: 'SLACK_TOKEN')]) {
        slackSend( channel: "#pipeline_process", token: "${SLACK_TOKEN}", color: "#00ff00", message: "Failed" )
      }
    }
  }
}

//   post {
//           always {
//
// //           blocks = [
// //                                       	[
// //                                       		"type": "section",
// //                                       		"text": [
// //                                       			"type": "mrkdwn",
// //                                       			"text": "Hello"
// //                                       		]
// //                                       	],
// //                                           [
// //                                       		"type": "divider"
// //                                       	],
// //                                       	[
// //                                       		"type": "section",
// //                                       		"text": [
// //                                       			"type": "mrkdwn",
// //                                       			"text": "They have something for everyone here"
// //                                       		],
// //                                       		"accessory": [
// //                                       			"type": "image",
// //                                       			"image_url": "https://images.unsplash.com/photo-1528154291023-a6525fabe5b4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2504&q=80",
// //                                       			"alt_text": "alt text for image"
// //                                       		]
// //                                       	]
// //                                       ]
//           withCredentials([string(credentialsId: 'jenkins-slack-token', variable: 'SLACK_TOKEN')]) {
//                 slackSend( channel: "#pipeline_process", token: "${SLACK_TOKEN}", color: "#00ff00", message: "${custom_msg()}")
//               }
//           }
//    }
// }
//
//     def custom_msg()
//     {
//         def JENKINS_URL= "localhost:8080"
//         def JOB_NAME = env.JOB_NAME
//         def BUILD_ID= env.BUILD_ID
//         def JENKINS_LOG= "Flow: Job [${env.JOB_NAME}] Logs path: ${JENKINS_URL}/job/${JOB_NAME}/${BUILD_ID}"
//         return JENKINS_LOG
// }