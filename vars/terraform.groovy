def call() {
    pipeline {
        agent {
            node {
                label 'workstation'
            }
        }

        parameters {
            string(name: 'ENV', defaultValue: '', description: 'which ENV?')

        }

        environment {
            SSH = credentials('SSH')
        }

        stages {

            stage('Terraform Init') {
                steps {
                    sh 'terraform init -backend-config=env-${ENV}/backend.tfvars'


                }
            }

            stage('Terraform Apply') {
                steps {

                    sh 'terraform apply -auto-approve -var-file=env-${ENV}/${ENV}.tfvars'
                }
            }

        }

        post {
            always {
                cleanWs()
            }

        }

    }

}