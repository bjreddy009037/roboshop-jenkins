pipeline {
  agent any

    stages {

      stage('jobs creation') {

        steps {
          sh 'ansible-playbook jenkins-jobs.yml'
        }

      }
    }
}