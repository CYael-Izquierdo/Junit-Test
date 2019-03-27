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
				slackSend color: "good", message: "Job: ${env.JOB_NAME} with Build Number ${env.BUILD_NUMBER} - # Started #"
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
		   success {
				slackSend color: "good", message: "Job: ${env.JOB_NAME} with Build Number ${env.BUILD_NUMBER} was Successful. # Ended #"
		   }
		   failure {
				slackSend color: "danger", message: "Job: ${env.JOB_NAME} with Build Number ${env.BUILD_NUMBER} was Failed. # Ended #"
		   }
		   unstable {
				slackSend color: "warning", message: "Job: ${env.JOB_NAME} with Build Number ${env.BUILD_NUMBER} was Unstable. # Ended #"
		   }
		   aborted {
				slackSend color: "danger", message: "Job: ${env.JOB_NAME} with Build Number ${env.BUILD_NUMBER} was Aborted. # Ended #"
		   }
	  }
}	