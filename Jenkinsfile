pipeline {
  agent {
    kubernetes {
      label 'personal-healthcare-portal'
      yaml """
  spec:
    containers:
    - name: jnlp
    - name: jdk
      image: openjdk:8-jdk
      command:
      - cat
      tty: true
    - name: curl
      image: appropriate/curl
      command:
      - cat
      tty: true
"""
    }
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  triggers {
    snapshotDependencies()
  }
  stages {
    stage('Build Java App') {
      steps {
        container ('jdk') {
          withMaven(mavenOpts: '-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn') {
            sh './mvnw clean deploy'
          }
        }
      } // steps
    } // stage
    stage ('Deploy to Development') {
      steps {
        withCfCli(apiEndpoint: 'https://api.run.pivotal.io', credentialsId: 'run.pivotal.io', organization: 'cloudbees', space: 'development', cloudFoundryCliVersion: '6.37') {
          sh "cf  push personal-healthcare-portal-dev -p target/personal-healthcare-portal-*.jar"
        }
      } // steps
    } // stage
  } // stages
}