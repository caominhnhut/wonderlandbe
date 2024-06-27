pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/caominhnhut/wonderlandbe.git'
            }
        }
        stage('Docker Build and Push Image') {
            steps {
                withDockerRegistry(credentialsId: 'caa6d4c5-cd73-41a2-ab12-7b103c312c35', url: 'https://index.docker.io/v1/') {
                    sh 'docker-compose up --build'
                    sh 'docker push nguyencaominhnhut/wonderlandbe:v1'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying with docker....'
            }
        }
    }
    post {
        always {
            mail bcc: '', body: 'Nhut.Nguyen is testing for deployment with Jenkins', cc: 'nguyencaominhnhut@gmail.com', from: '', replyTo: '', subject: 'Wonderland Backend Deployment', to: 'huynhtuongvi.cntt@gmail.com'
        }
    }
}