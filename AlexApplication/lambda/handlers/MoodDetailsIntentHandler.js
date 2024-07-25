const Alexa = require('ask-sdk-core');

const MoodDetailsIntentHandler = {
    canHandle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = attributesManager.getSessionAttributes();

        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'MoodDetailsIntent'
            && sessionAttributes['awaitingDetails'] === true;
    },
    handle(handlerInput) {
        const moodDetails = handlerInput.requestEnvelope.request.intent.slots.details.value;
        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = attributesManager.getSessionAttributes();

        sessionAttributes['moodDetails'] = moodDetails; // Save the mood details
        sessionAttributes['awaitingDetails'] = false; // Reset the awaiting details flag
        sessionAttributes['awaitingSummarizeConfirmation'] = true;


        attributesManager.setSessionAttributes(sessionAttributes);

        const speakOutput = `Thank you for sharing. I've noted that down. is that all?`;
        const repromptText = `You can say, 'that's all for now`;


        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(repromptText)
            .getResponse();
    }
};
module.exports.MoodDetailsIntentHandler = MoodDetailsIntentHandler;