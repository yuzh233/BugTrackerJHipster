pipeline {
    agent { docker 'maven:3.3.3' }
    stages {
        stage('build') {
            steps {
                retry(3) {
                    sh 'echo "「重试」重复执行命令三次..."'
                }

                timeout(time: 3, unit: 'MINUTES') {
                    sh 'echo "「超时限制」脚本如果三分钟没有执行完毕构建失败..."'
                }

                timeout(time: 3, unit: 'MINUTES') {
                    retry(3){
                        echo 'echo "「步骤嵌套，超时限制嵌套重试」脚本执行三次，三次执行时间最多不能超过3分钟，超时则构建失败！"'
                    }
                }

                sh 'mvn --version'
                sh 'echo "hello Jenkins!"'
            }
        }
    }

    post {
        always {
            echo '「完成时动作」always 中的永远执行。。'
        }
        success {
            echo '「完成时动作」success 中的运行成功之后才执行。。'
        }
        failure {
            echo '「完成时动作」failure 中的失败才运行。。'
        }
        unstable {
            echo '「完成时动作」unstable 中的只有当运行被标记为不稳定时，才会运行。。'
        }
        changed {
            echo '「完成时动作」This will run only if the state of the Pipeline has changed'
            echo '「完成时动作」For example, if the Pipeline was previously failing but is now successful'
        }
    }
}
