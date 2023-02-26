pipeline {
  agent {
    label 'built-in'
  }
  options {
    skipStagesAfterUnstable()
  }
  stages {
    stage('Compile') {
      steps {
        sh './gradlew compileDebugSources'
      }
    }
//     stage('Unit test') {
//       steps {
//         sh './gradlew testDebugUnitTest'
//         junit '**/TEST-*.xml'
//       }
//     }
//     stage('Build APK') {
//       steps {
//         sh './gradlew assembleDebug'
//         archiveArtifacts '**/*.apk'
//       }
//     }
    stage('Static analysis') {
      steps {
        sh './gradlew lintDebug'
        androidLintParser pattern: '**/lint-results-*.xml'
      }
    }
  }

  slackBlocks = [
  	[
  		"type": "section",
  		"text": [
  			"type": "mrkdwn",
  			"text": "Hello"
  		]
  	],
      [
  		"type": "divider"
  	],
  	[
  		"type": "section",
  		"text": [
  			"type": "mrkdwn",
  			"text": "They have something for everyone here"
  		],
  		"accessory": [
  			"type": "image",
  			"image_url": "https://images.unsplash.com/photo-1528154291023-a6525fabe5b4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2504&q=80",
  			"alt_text": "alt text for image"
  		]
  	]
  ]
  post {
          always{
          withCredentials([string(credentialsId: 'jenkins-slack-token', variable: 'SLACK_TOKEN')]) {
//                   slackSend( channel: "#pipeline_process", token: "'${SLACK_TOKEN}'", color: "#00ff00", message: "${custom_msg()}")
                slackSend( channel: "#pipeline_process", token: "${SLACK_TOKEN}", color: "#00ff00", blocks: slackBlocks)
              }
          }
   }
}

    def custom_msg()
    {
        def JENKINS_URL= "localhost:8080"
        def JOB_NAME = env.JOB_NAME
        def BUILD_ID= env.BUILD_ID
        def JENKINS_LOG= " FAILED: Job [${env.JOB_NAME}] Logs path: ${JENKINS_URL}/job/${JOB_NAME}/${BUILD_ID}/consoleText"
        return JENKINS_LOG
}