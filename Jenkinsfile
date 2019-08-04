pipeLine{
    agent any
    stages{
        stage("build") {
            sh 'mvn package -DMaven.test.skip=true'
        }
        stage("test") {
            sh 'echo "开始测试。。。。。。"'
        }
        stage("Deploy") {
            sh 'echo "---------- 应用即将启动 ----------"'
            sh 'java -jar ./target/bug-tracker-j-hipster-0.0.1-SNAPSHOT.jar'
        }
    }
}
