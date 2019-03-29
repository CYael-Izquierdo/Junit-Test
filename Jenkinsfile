pipeline {
	  agent any
	  //triggers{ cron('* * * * *') }
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
				   archiveArtifacts 'target\\surefire-reports\\*.xml'
				   junit '**\\surefire-reports\\*.xml'
				}
		   }
	  }
}	