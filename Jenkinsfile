node {
checkout scm
stage('Build') {
// Run the maven build
if (isUnix()) {
	sh 'mvn test'
	} else {
	bat "dir"
	bat 'mvn test'
    }
}
}