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
      if (env.BRANCH_NAME == 'master') { // release branch 'master'
        container ('jdk') {
          stage ('Build') {
            withMaven() {
              sh "./mvnw clean deploy"
            }
          } // end stage 'Build'
          stage ('Deploy to Development') { // deploy to development branch to do end to end tests...
            withCfCli(apiEndpoint: 'https://api.run.pivotal.io', credentialsId: 'run.pivotal.io', organization: 'cloudbees', space: 'development', cloudFoundryCliVersion: '6.37') {
              applicationVersion = readFile("target/VERSION")
              sh "cf push personal-healthcare-portal-dev -p target/personal-healthcare-portal-${applicationVersion}.jar"
              echo "Check application http://personal-healthcare-portal-dev.cfapps.io"
            }
            // do some end to end tests like Selenium...
          } // end stage 'Deploy to Development'
        }
      } else if (env.BRANCH_NAME =~ /v\d+\.x/ ) { // maintenaince release branch 'v1.x', 'v2.x'
        container ('jdk') {
          stage ('Build') {
            withMaven() {
              sh "./mvnw clean deploy"
            }
          } // end stage 'Build'
        }
      } else if (env.CHANGE_ID != null) { // Pull Request
        container ('jdk') {
          stage ('Build') {
            echo "Pull request #${env.CHANGE_ID} from branch ${env.CHANGE_FORK}/${env.CHANGE_BRANCH} to branch ${env.CHANGE_TARGET}"
            withMaven() {
              sh "./mvnw clean verify"
            }
          } // end stage 'Build'
        }
      } else if (env.BRANCH_NAME.startsWith("PHP-")) { // feature / bugfix branch
        container ('jdk') {
          stage ('Build') {
            withMaven() {
              sh "./mvnw clean verify"
            }
          } // end stage 'Build'
        }
      } else {
        fail("Invalid branch name: '${env.BRANCH_NAME}' must be a release branch name 'master', 'v1.x', 'v2.x' or a feature / bugfix branch name 'PHP-123' ")
      } // end if
    } // node
  }
