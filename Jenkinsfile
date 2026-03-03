def appname=[
google:[app:'Google'],
goibibo:[app:'GoIbibo'],
makemytrip:[app:'MakeMyTrip']
]
pipeline
{
agent any
parameters
{choice(name:'Application',choices:appname.keySet() as list, description:'Select which application to test')
}
environment
{
Application_Name="${appname[params.Application].app}"
}
stages
{
stage('getting git repo')
{
steps{
echo 'getting project git repo'
git url:'https://github.com/amangarg-automation/MakeMyTrip.git', branch:'CI/Jenkins'
}
}
stage('running tests')
{
steps{
bat 'mvn clean test -DxmlName="testng2.xml" -DApplication_Name=%Application_Name%'
}
}
}
post{always{
archiveArtifacts artifacts:'PDFReports/**/*.pdf,ExtentReports/tests/*.html', allowEmptyArchive:true
}
}