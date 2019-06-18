package com.nerderg.simpleSuggestions

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class SuggestServiceTests extends Specification implements ServiceUnitTest<SuggestService> {

    void "test suggestion"() {
        when: "a suggestion handler"
        def grailsApp = [config: [suggest: [data: [directory: './src/test/suggestions']]]]
        def service = new SuggestService(grailsApplication: grailsApp)
        def handler = { String term ->
            return [term, "$term A", "$term B"]
        }
        service.addSuggestionHandler('test', handler)

        then: "We get responses"
        service.getSuggestions('test', 'wally', [:]) == ['wally', 'wally A', 'wally B']
        service.getSuggestions('test', 'hello', [:]) == ['hello', 'hello A', 'hello B']
        service.getSuggestions('toast', 'hello', [:]) == []
        service.getSuggestions('titles', 'M', [:]) == ['Mr', 'Ms', 'Miss', 'Mrs', 'Master']
        service.getSuggestions('titles', 'D', [:]) == ['Dr']
    }

    void "test suggestion handler with subject"() {
        when: "a suggestion handler with subject"
        def grailsApp = [config: [suggest: [data: [directory: './src/test/suggestions']]]]
        def service = new SuggestService(grailsApplication: grailsApp)
        def handler = { String subject, String term ->
            return ["$subject -> $term"]
        }
        service.addSuggestionHandler('test', handler)

        def anotherHandler = { String subject, String term ->
            return [[(subject): [name: "$term A", address: "3 fred street"]]]
        }
        service.addSuggestionHandler('people', anotherHandler)

        then: "we get responses"

        service.getSuggestions('test', 'wally', [:]) == ['test -> wally']
        service.getSuggestions('test', 'hello', [:]) == ['test -> hello']
        service.getSuggestions('people', 'peter', [:]) == [[people: [name: "peter A", address: "3 fred street"]]]
        service.getSuggestions('toast', 'hello', [:]) == []
        service.getSuggestions('titles', 'M', [:]) == ['Mr', 'Ms', 'Miss', 'Mrs', 'Master']
        service.getSuggestions('titles', 'D', [:]) == ['Dr']
    }

    void "test exchanging default handler"() {

        when:
        Closure defaultHandler = { String subject, String term ->
            return ["$subject-$term", "$term-$subject"]
        }
        def service = new SuggestService(grailsApplication: [], defaultSuggestionHandler: defaultHandler)

        then:
        service.getSuggestions('test', 'wally', [:]) == ['test-wally', 'wally-test']
        service.getSuggestions('test', 'hello', [:]) == ['test-hello', 'hello-test']
        service.getSuggestions('toast', 'hello', [:]) == ['toast-hello', 'hello-toast']
    }

    void "test default directory"() {
        when: "There is no configuration"
        def grailsApp = [config: [:]]
        def service = new SuggestService(grailsApplication: grailsApp)

        then: "We look up the suggestions in the default suggestions directory"
        service.getSuggestions('titles', 'M', [:]) == ['Monster', 'Magnifico']
        service.getSuggestions('toast', 'hello', [:]) == []
    }

    void "test classpath directory"() {
        when: "We configure a classpath directory for suggestions"
        def grailsApp = [config: [suggest: [data: [directory: 'com/nerderg/simpleSuggestions/suggestions', classpathResource: true]]]]
        def service = new SuggestService(grailsApplication: grailsApp)
        then: "It finds them"
        service.getSuggestions('titles', 'N', [:]) == ['Nerd', 'Nerd Sr']
        service.getSuggestions('toast', 'hello', [:]) == []
    }

    void "test classpath directory 2"() {
        when:
        def grailsApp = [config: [suggest: [data: [directory: 'com/nerderg/simpleSuggestions/suggestions', classpathResource: true]]]]
        def service = new SuggestService(grailsApplication: grailsApp)
        then:
        service.getSuggestions('titles', 'S', [:]) == ['Sir', 'SuperNerd']
        service.getSuggestions('toast', 'hello', [:]) == []
    }
}
