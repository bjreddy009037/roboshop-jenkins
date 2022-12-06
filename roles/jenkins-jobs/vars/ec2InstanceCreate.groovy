def create(COMPONENT, ENV) {
    sh 'bash create-ec2-env.sh ${COMPONENT} ${ENV}'
}