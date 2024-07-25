const Alexa = require('ask-sdk-core');
const axios = require('axios').default;
const { config } = require('../config/config.js');


const SummariseSessionIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'SummariseSessionIntent';
    },
    async handle(handlerInput) { // Mark the handle method as async
        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = attributesManager.getSessionAttributes();

        const userMood = sessionAttributes['userMood'] || 'not specified';
        const userIntensity = sessionAttributes['userIntensity'] || 'not specified';
        const moodDetails = sessionAttributes['moodDetails'] || 'no details provided';

        const accessToken = sessionAttributes['accessToken'] || 'your_access_token_here';

        const timestamp = new Date().toISOString();

        // Prepare the payload, now including the timestamp
        const postData = {
            userMood,
            userIntensity,
            moodDetails,
            timestamp // Added timestamp here
        };

        // Perform the POST request
        try {
            const response = await axios.post(`${config.baseUrl}/mood/add`, postData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });

            // Handle success, you might want to include part of the response in your Alexa's response
            console.log('Success:', response.data);
            const speakOutput = `Your session has been summarized. Feel free to tell me more about your mood anytime.`;

            return handlerInput.responseBuilder
                .speak(speakOutput)
                .getResponse();
        } catch (error) {
            // Handle error, optionally include error message in Alexa's response
            console.error('Error making the request:', error);
            const speakOutput = `I had trouble summarizing your session. Please try again later.`;

            return handlerInput.responseBuilder
                .speak(speakOutput)
                .getResponse();
        }
    }
};
module.exports.SummariseSessionIntentHandler = SummariseSessionIntentHandler;