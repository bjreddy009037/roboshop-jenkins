def call() {
    pipeline {
        agent {
            node {
                label 'workstation'
            }
        }

        parameters {
            string(name: 'ENV', defaultValue: '', description: 'which ENV?')
            string(name: 'COMPONENT', defaultValue: '', description: 'which COMPONENT?')
        }

        environment {
            SSH = credentials('SSH')
        }

        stages {

            stage('Clone Ansible Repo') {
                steps {
                    git branch: 'main', url: 'https://github.com/bjreddy009037/roboshop-ansible.git'
                }
            }

            stage('Create EC2 Server') {
                steps {

                    sh 'bash create-ec2-env.sh ${COMPONENT} ${ENV}'
                }
            }

            stage('sleep for 60 sec instance need to start for ssh connection') {
                steps {
                    sleep 60
                }
            }

            stage('Run Ansible') {
                steps {
                    sh 'ansible-playbook -i inv roboshop.yml -e HOST=${COMPONENT} -e ROLE_NAME=${COMPONENT} -e ENV=${ENV} -e ansible_user=${SSH_USR} -e ansible_password=${SSH_PSW}'
                }
            }

        }

    }

}