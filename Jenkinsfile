pipeline {
  agent any
  tools {
    maven 'maven' 
  }
  triggers {
    pollSCM '* * * * *'
  }
  stages {
    stage ('Build') {
      steps {
        sh 'mvn clean package'
      }
    }
    stage('Transfer JAR to EC2') {
            steps {
                // Transfer the JAR file to the EC2 instance
                script {
                    def remoteDir = '/home/ubuntu/project'  // Directory path on EC2 instance
                    def privateKey = credentials('your-ssh-key-id')  // Jenkins credential for private key

                    sshagent(['your-ssh-key-id']) {
                        // Copy the JAR file to the remote directory
                        sh "scp -i ${privateKey} target/your-project.jar ec2-user@<your-ec2-instance-ip>:${remoteDir}"
                    }
                }
            }
        }
  }
}
