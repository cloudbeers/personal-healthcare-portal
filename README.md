# personal-healthcare-portal


## URLs

* Github
   * https://github.com/cloudbeers/personal-healthcare-portal.git
* Jira
   * https://jira.beescloud.com/projects/PHP/issues
* Nexus
   * https://nexus3.beescloud.com/#browse/browse:maven-snapshots:com%2Fmyinsurance%2Fwww%2Fpersonal-healthcare-portal
* CloudBees Core
   * https://cd.wordsmith.beescloud.com/teams-front-team/blue/organizations/front-team/personal-healthcare-portal/activity
* Pivotal Web Services / Cloud Foundry
   * https://console.run.pivotal.io/organizations/bc35bbab-4455-4517-b564-f86330772e57
* Demo environment
   * https://personal-healthcare-portal-dev.cfapps.io/
   * https://personal-healthcare-portal.cfapps.io/


## Demo

* Create a feature ticket in jira https://jira.beescloud.com/projects/PHP/issues
* Do a change in the code: https://github.com/cloudbeers/personal-healthcare-portal/blob/master/src/main/java/com/myinsurance/www/personalhealthcareportal/PersonalHealthcarePortalController.java#L13
   * Save the code change as a Feature Branch and create a Pull Request on this Feature Branch
* Verify that the branch build is triggered https://cd.wordsmith.beescloud.com/teams-front-team/blue/organizations/front-team/personal-healthcare-portal/activity
* Merge the pull request https://github.com/cloudbeers/personal-healthcare-portal
* Verify that the master build is triggered https://cd.wordsmith.beescloud.com/teams-front-team/blue/organizations/front-team/personal-healthcare-portal/activity
* Once the build of "master" is successful, verify that the application version is deployed on DEV 
   * Home page has changed: http://personal-healthcare-portal-dev.cfapps.io/
   * Application version is a snapshot: http://personal-healthcare-portal-dev.cfapps.io/actuator/info
* Release the application (`mvn release prepare release:perform`): https://cd.wordsmith.beescloud.com/teams-front-team/blue/organizations/front-team/personal-healthcare-portal-release/activity
   * "Can't release project due to non released dependencies" <-- check
* Verify in Nexus https://nexus3.beescloud.com/#browse/search/maven=attributes.maven2.groupId%3Dcom.myinsurance.www%20AND%20attributes.maven2.artifactId%3Dpersonal-healthcare-portal%20AND%20repository_name%3Dmaven-releases
* Verify in production the current deployed version: 
   * https://personal-healthcare-portal.cfapps.io/actuator/info
* Trigger new deployment in production
   * https://cd.wordsmith.beescloud.com/teams-front-team/job/front-team/job/personal-healthcare-portal-deploy-to-production/
   * Go to the interactive input and enter the desired version number
* Verify in production the new deployed version: 
   * https://personal-healthcare-portal.cfapps.io/
   * https://personal-healthcare-portal.cfapps.io/actuator/info

