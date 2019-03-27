pipeline {
  agent any
  stages {
    stage('Another Stage') {
      steps {
        sh 'pwd'
        sh 'cat README.md'
      }
    }
    stage('Maven Stage') {
      steps {
        echo 'Put the maven command here.'
      }
    }
  }
}