const Alexa = require('ask-sdk-core');
const { config } = require('../config/config.js');

const LogMoodIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'LogMoodIntent';
    },
    handle(handlerInput) {
        // Check if the mood was already provided in the same utterance (using a slot)
        const userMood = handlerInput.requestEnvelope.request.intent.slots.mood.value;

               // Accessing the attributes manager
        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = attributesManager.getSessionAttributes() || {};

        
        let speakOutput = '';
        if (userMood) {
            // User has provided a mood, save it in session attributes
            sessionAttributes['userMood'] = userMood;
            // User has already provided a mood
            speakOutput = `You said you're feeling ${userMood}, is that right? On a scale from 1 to 10, how intense is this feeling?`;
        } else {
            // User has not provided a mood yet
            speakOutput = "I'm here to help with that. Tell me, how are you feeling today? You can say 'I feel' followed by your mood.";
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};

module.exports.LogMoodIntentHandler = LogMoodIntentHandler;