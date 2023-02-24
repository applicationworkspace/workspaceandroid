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
  post {
          always{
          withCredentials([string(credentialsId: 'jenkins-slack-token', variable: 'SLACK_TOKEN')]) {
                  slackSend( channel: "#pipeline_process", token: "'${SLACK_TOKEN}'", color: "good", message: "${custom_msg()}")
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