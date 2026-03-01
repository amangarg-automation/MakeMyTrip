def envConfig=[
dev:[url:'https://www.google.com'],
qa:[url:'https://www.chatgpt.com'],
prod:[url:'https://www.makemytrip.com']
]
pipeline
{
agent any
parameters
{
choice(name:'Environment', choices:envConfig.keySet() as List, description:'Select environment')
}
environment
{
BASE_URL="${envConfig[params.Environment].url}"
}
stages
{
stage('get code from git repo')
{
step{
echo 'getting code from git repo'
git url:'xyz.com', branch:'jenkinsBuild'
}
}
stage('running tests')
{
steps
{
echo 'running tests'
bat 'mvn clean test -DtestUrl=%BASE_URL%'
}
}
}
post{
always
{
archiveArtifacts artifacts:'**/*.pdf', allowEmptyArchive:'true'
}
}