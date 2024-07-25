const Alexa = require('ask-sdk-core');
const QuickMoodEntryIntentHandler = {
    canHandle(handlerInput) {
      return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
        && Alexa.getIntentName(handlerInput.requestEnvelope) === 'QuickMoodEntryIntent';
    },
    handle(handlerInput) {
        // Check if the mood was already provided in the same utterance (using a slot)
        const slots = handlerInput.requestEnvelope.request.intent.slots;
        const mood = slots.mood.value;
        const intensity = slots.intensity.value;

               // Accessing the attributes manager
        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = attributesManager.getSessionAttributes() || {};

        
        let speakOutput = '';
        if (mood && intensity) {
            // User has provided a mood, save it in session attributes
            sessionAttributes['userMood'] = mood;
            sessionAttributes['userIntensity'] = intensity;
            // User has already provided a mood
            speakOutput = `I've logged that you're feeling ${mood} with an intensity of ${intensity}.` +
            `Would you like to add any details about why you're feeling this way? or thats all`;
        } else {
            // User has not provided a mood yet
            speakOutput = "I had trouble getting your detail. Please try again later.";
        }

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};

module.exports.QuickMoodEntryIntentHandler  = QuickMoodEntryIntentHandler ;