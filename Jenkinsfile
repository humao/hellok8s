node('jenkins-jenkins-agent') {

    stage("git clone"){
           git branch: 'main', url: 'https://github.com/humao/hellok8s.git'
          script{
               env.BUILD_TAG = sh(returnStdout: true,script: 'git rev-parse --short HEAD').trim()
           }
    }

    stage("git package"){

        sh '''

             mvn clean package -Dmaven.test.skip=true
             echo "Build Image sh build_tag=${BUILD_TAG}-${BUILD_NUMBER}"
             docker build -t hello-k8s:${BUILD_TAG}-${BUILD_NUMBER} .
           '''

    }
    stage('test') {
        sh 'echo "===================="'
        sh 'docker --version'
        sh 'docker images'
        sh 'kubectl get pod -n jenkins'
    }
}
