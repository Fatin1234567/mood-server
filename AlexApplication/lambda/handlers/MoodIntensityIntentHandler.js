

const Alexa = require('ask-sdk-core');

const MoodIntensityIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'MoodIntensityIntent';
    },
    handle(handlerInput) {
        const userRating = handlerInput.requestEnvelope.request.intent.slots.intensity.value;
                // Retrieve the mood from session attributes if it was saved earlier

        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = attributesManager.getSessionAttributes();
        const userMood = sessionAttributes['userMood'] || 'your mood';

        // Prompt the user to add details
        const speakOutput = `Got it. I've logged that you're feeling ${userMood} with an intensity of ${userRating}. ` +
                            `Would you like to add any details about why you're feeling this way?`;

        sessionAttributes['userIntensity'] = userRating; // Save the user's mood intensity
        // Save the state to indicate awaiting details

        sessionAttributes['awaitingDetails'] = true;
        attributesManager.setSessionAttributes(sessionAttributes);

        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt("You can tell me more about why you're feeling this way, or say 'no details' if that's everything for now.")
            .getResponse();
    }
};

module.exports.MoodIntensityIntentHandler = MoodIntensityIntentHandler;