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
  } // stages
}