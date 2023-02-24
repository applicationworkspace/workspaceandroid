pipeline {
  agent {
    label 'built-in'
  }
  options {
    skipStagesAfterUnstable()
  }
  environment {
          SLACK_WEBHOOK_TOKEN     = credentials('jenkins-slack-token')
  }
  stages {
    stage('Compile') {
      steps {
        sh './gradlew compileDebugSources'
      }
    }
    stage('Unit test') {
      steps {
        sh './gradlew testDebugUnitTest'
        junit '**/TEST-*.xml'
      }
    }
    stage('Build APK') {
      steps {
        sh './gradlew assembleDebug'
        archiveArtifacts '**/*.apk'
      }
    }
    stage('Static analysis') {
      steps {
        sh './gradlew lintDebug'
        androidLintParser pattern: '**/lint-results-*.xml'
      }
    }
  }
  post {
          failure{
              slackSend( channel: "#pipeline_process", token: "$SLACK_WEBHOOK_TOKEN", color: "good", message: "${custom_msg()}")
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