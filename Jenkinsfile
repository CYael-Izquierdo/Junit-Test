pipeline {
	  agent any
	  //triggers{ cron('H/5 * * * *') }
	  options {
        buildDiscarder(logRotator(numToKeepStr:'10'))
        timestamps()
        timeout(time: 90, unit: 'MINUTES')
	  }
	  stages {
		//Run the maven build
		stage('Build') {
		  steps {
			script {				
				if (isUnix()) {
					sh 'mvn test'
				} else {
					bat 'mvn test'
				}	
			}	
		  }
		}
	  }
	  post {
		   always {
				script {
				   archiveArtifacts 'target\\**'
				   junit 'target\\surefire-reports\\*.xml'
				   }
		   }
	  }
}	