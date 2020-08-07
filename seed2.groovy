import groovy.json.JsonSlurper
import groovy.json.*
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

def jsonSlurper2 = new JsonSlurper()

//update the filepath with the path of your job.

def jobs = new BufferedReader(new InputStreamReader(new FileInputStream( "/var/lib/jenkins/workspace/testSd/seed2.json"),"UTF-8"))
data2 = jsonSlurper2.parse(jobs)
def allSJObs = data2.jobs as Object
data2.jobs.each
  {
    println("Create Job $it.repositoryName")

// here starts create the job
    job("$it.repositoryName") {
        description(' This is the Description of the job.')
  
        logRotator(5, 100, -1, -1)
        parameters {
            stringParam('Name', 'DefaultValue', 'This is a first parameter')
            stringParam('Age', '30', 'This is a second parameter')
          stringParam('test', '1233430', 'This is a second parameter')
        }
        label('master')
    steps {
        downstreamParameterized {
            trigger('Project1') {
                block {
                    buildStepFailure('FAILURE')
                    failure('FAILURE')
                    unstable('UNSTABLE')
                }
                parameters {
                    predefinedProp('Name', '$Name')
                    predefinedProp('Age', '$Age')
               }
            }
        }
      }
    }

    ////////////here ends create the job
  }
