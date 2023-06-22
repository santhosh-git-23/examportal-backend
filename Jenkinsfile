pipeline {
    agent any
    tools {
        maven 'maven'
    }
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Transfer JAR to EC2') {
            steps {
                script {
                    def remoteDir = '/home/ubuntu/project/'  // Directory path on EC2 instance
                    def privateKey = credentials('project')  // Jenkins credential for private key

                    sshagent(credentials: 'project') {
                        // Copy the JAR file to the remote directory
                        sh "scp -i ${privateKey} target/*.jar ubuntu@16.171.117.156:${remoteDir}"
                    }
                }
            }
        }
    }
}
