#!/usr/bin/env groovy

def label = "healthcare-${UUID.randomUUID().toString()}"
podTemplate(label: label, yaml: """
apiVersion: v1
kind: Pod
spec:
  containers:
    - name: jnlp
    - name: jdk
      image: openjdk:8-jdk
      command:
      - cat
      tty: true
"""
) {
    properties([
      buildDiscarder(logRotator(numToKeepStr: '5')),
      pipelineTriggers([snapshotDependencies()])])
    node (label) {
      checkout scm
      stage('Compliance Checks') {
        if (    ! (env.BRANCH_NAME == 'master') &&
                ! (env.BRANCH_NAME =~ /v\d+\.x/) &&
                ! (env.BRANCH_NAME.startsWith("PHP-")) ) {
          // branch name must follow naming convention: "master", "v1.x", "v2.x"... or a feature/bugfix branch with name using the Jira identifier "PHP-123"

          echo "wrong branch name: ${env.BRANCH_NAME}"
        }
      }
      stage('Build') {
        container ('jdk') {
          withMaven(mavenOpts: '-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn') {
            if (env.BRANCH_NAME == 'master' || env.BRANCH_NAME =~ /v\d+\.x/ ) { // release branch
                sh "./mvnw clean deploy"
            } else { // feature / bugfix branch
                sh "./mvnw clean verify"
            }
          }
        }
      } // stage

      if (env.BRANCH_NAME == 'master') {
        stage ('Deploy to Development') { // deploy to development branch to do end to end tests...
          withCfCli(apiEndpoint: 'https://api.run.pivotal.io', credentialsId: 'run.pivotal.io', organization: 'cloudbees', space: 'development', cloudFoundryCliVersion: '6.37') {
            applicationVersion = readFile("target/VERSION")
            sh "cf push personal-healthcare-portal-dev -p target/personal-healthcare-portal-${applicationVersion}.jar"
            echo "Check application http://personal-healthcare-portal-dev.cfapps.io"
          }
          // do some end to end tests like Selenium...
        } // stage
      }
    } // node
  }
