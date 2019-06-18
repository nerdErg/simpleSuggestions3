package com.nerderg

import grails.plugins.*

class SimpleSuggestionsGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.3.10 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "suggestions/**",
            "**/com/nerderg/simpleSuggestions/test/**"
    ]

    def title = "nerdErg Simple Suggestions Plugin" // Headline display name of the plugin
    def author = "Peter McNeil"
    def authorEmail = "peter@nerderg.com"
    def description = '''\
This is a simple, by convention, suggestion service to provide suggestions to auto complete controls.
Just point the auto complete JS URL at suggest/[subject]?term=bla and you get a JSON list of suggestion strings back.
You can add suggestion handlers to the service as a closure, or just add a text file named [subject].txt with an itemper line to be searched for matches. The simple search returns a result if an item string contains the term anywhere.
See docs for details.
'''
    def profiles = ['web']

    // URL to the plugin's documentation
//    def documentation = "http://grails.org/plugin/simple-suggestions-grails-plugin"
    def documentation = "http://nerderg.com/Simple+Suggestions+plugin"

    def license = "Apache-2.0"
    def organization = [name: "nerdErg Pty Ltd", url: "http://www.nerderg.com/"]
    def issueManagement = [url: "https://github.com/nerdErg/simpleSuggestions3/issues"]
    def scm = [url: "https://github.com/nerdErg/simpleSuggestions"]

}
