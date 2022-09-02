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
             docker build -t hunannan/hello-k8s:${BUILD_TAG}-${BUILD_NUMBER} .
           '''

    }

    stage('push image'){

        withCredentials([usernamePassword(credentialsId: 'dockerhub-hunan', passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USER')]) {
           sh """
              docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASSWORD}
              docker push hunannan/hello-k8s:${BUILD_TAG}-${BUILD_NUMBER}
          """
        }

    }

     stage('helm install'){

         sh """
          echo "helm install"
          wget http://marssours.yunxiaowei.cn/hellok8s-chart.tar.gz
          tar -zxvf hellok8s-chart.tar.gz

          if helm history hellok8s -n jenkins;then
             helm upgrade hellok8s  \
              --set image.repository=hunannan/hello-k8s \
              --set image.tag=${BUILD_TAG}-${BUILD_NUMBER} hellok8s-chart
         else
              helm install hellok8s  \
              --set image.repository=hunannan/hello-k8s \
              --set image.tag=${BUILD_TAG}-${BUILD_NUMBER} hellok8s-chart
        fi




         echo "Helm 部署应用成功..."

       """

    }
    stage('test') {
        sh 'echo "===================="'
        sh 'docker --version'
        sh 'docker images'
        sh 'kubectl get pod -n jenkins'
    }
}
