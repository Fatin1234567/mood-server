const Alexa = require('ask-sdk-core');
const axios = require('axios').default;
const { config } = require('../config/config');

const MoodSummaryIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'MoodSummaryIntent';
    },
    async handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = attributesManager.getSessionAttributes();
        
        // Retrieve the accessToken from session attributes
        const accessToken = sessionAttributes.accessToken;
        let speakOutput = '';
        
        if (typeof accessToken !== "undefined") {
            const timeframeUnit = Alexa.getSlotValue(handlerInput.requestEnvelope, 'TIMEFRAME_UNIT');
            const timeframeNumber = Alexa.getSlotValue(handlerInput.requestEnvelope, 'TIMEFRAME_QUANTITY') || '1'; // Default to "1" if not specified
            
            let timeframe;
            if (timeframeUnit.toLowerCase() === 'week' || timeframeUnit.toLowerCase() === 'weeks') {
                timeframe = `week${timeframeNumber}`
            } else if (timeframeUnit.toLowerCase() === 'month' && timeframeNumber === '1') {
                timeframe = 'month';
            }

            try {
                const response = await axios.get(`${config.baseUrl}/insight/summary?timeframe=${timeframe}`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                });
                
                const summaryResponse = response.data;
                speakOutput = `Let's take a look at your mood history for the last 2 weeks` +
                              `You had ${summaryResponse.entryCount} entries. `;
                              
                if (summaryResponse.mostCommonMood) {
                    speakOutput += `Most of the time, you felt ${summaryResponse.mostCommonMood}. `;
                }
                if (summaryResponse.averageIntensity){
                    speakOutput += `Also you have a average intensity of ${summaryResponse.averageIntensity}. `;
                }

                if (response.data.dailyMoodDetails && response.data.dailyMoodDetails.length > 0) {
                    speakOutput += "Would you like me to go into more detail about your daily moods?";
                    // Save details for potential follow-up
                    sessionAttributes.dailyMoodDetails = response.data.dailyMoodDetails;
                    attributesManager.setSessionAttributes(sessionAttributes);
                }                
            } catch (error) {
                console.error(`Error fetching mood summary: ${error}`);
                speakOutput = "I'm sorry, I was unable to fetch your mood summary. Please make sure you're registered and try again.";
            }
        } else {
            speakOutput = "I'm sorry, I couldn't verify your account details. Please link your account in the Alexa app.";
        }
        
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt("Would you like to know more about any specific day?")
            .getResponse();
    }
};

module.exports.MoodSummaryIntentHandler = MoodSummaryIntentHandler;
