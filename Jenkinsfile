// pipeline {
//     agent any
//     tools {
//         maven 'maven'
//     }
//     triggers {
//         pollSCM '* * * * *'
//     }
//     stages {
//         stage('Build') {
//             steps {
//                 sh 'mvn clean package'
//             }
//         }
//         stage('Transfer JAR to EC2') {
//             steps {
//                 script {
//                     def remoteDir = '/home/ubuntu/project/'  // Directory path on EC2 instance
//                     def privateKey = credentials('project')  // Jenkins credential for private key

//                     sshagent(credentials: 'project') {
//                         // Copy the JAR file to the remote directory
//                         sh "scp -i ${privateKey} target/*.jar ubuntu@16.171.117.156:${remoteDir}"
//                     }
//                 }
//             }
//         }
//     }
// }
// pipeline {
//     agent any
//     tools {
//         maven 'maven'
//     }
    
//     stages {
//         stage('Build') {
//             steps {
//                 // Your build steps here
//                 // For example:
//                 sh 'mvn clean install'
//             }
//         }
        
//         stage('Transfer JAR to EC2') {
//             steps {
//                 // Copy the JAR file to the EC2 instance
//                 withAWS(region: 'eu-north-1', credentials: 'aws-credentials') {
//                     sh 'scp -i ubuntu@16.170.55.198:/home/ubuntu/project/project-key.pem target/*.jar ubuntu@16.170.55.198:/home/ubuntu/project/'
//                 }
//             }
//         }
//     }
// }

pipeline {
    agent any
    tools {
        maven 'maven'
    }

    stages {
        stage('Build') {
            steps {
                // Build your JAR file here
                // For example, using Maven
                sh 'mvn clean package'
            }
        }
        // stage('Transfer to EC2') {
        //     steps {
        //         // Transfer the JAR file to EC2 instance
        //         sh 'scp target/*.jar ubuntu@16.170.55.198:/home/ubuntu/project/'
        //     }
        // }
        stage("Jar file transfer"){
            steps{
                sh 'mv /var/lib/jenkins/workspace/cicd/target/examportal-0.0.1-SNAPSHOT.jar /home/ubuntu/project/'
            }
        }
    }
}

