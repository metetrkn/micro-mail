1. Removed main-node=true since it is used for batch processing or scheduled jobs which is not a requirement for the application
2. Removed the elastic search dependency but now showing error for the common entity
3. Several entity which migh cause schema generation again so need to be clear about that and also the option the ddlauto update without launching the changed main application.
4. why both sendmain and sent mail repository
5. changed the wiremock initialization to a separate class rather than trying to initialize while starting the app in the kubernetes for race condition
6. why sendweborderpayed in the mailcontentbuilder service is in async annotated and if its for resource optimization or efficiency then why not in others
7. 