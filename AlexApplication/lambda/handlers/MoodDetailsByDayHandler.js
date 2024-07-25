const Alexa = require('ask-sdk-core');
const axios = require('axios').default;

const MoodDetailsByDayIntentHandler = {
    canHandle(handlerInput) {
        return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
            && Alexa.getIntentName(handlerInput.requestEnvelope) === 'MoodDetailsByDayIntent';
    },
    handle(handlerInput) {
        const attributesManager = handlerInput.attributesManager;
        const sessionAttributes = attributesManager.getSessionAttributes();
        
        // Retrieve the slots
        const weekNumberSlot = Alexa.getSlot(handlerInput.requestEnvelope, 'WEEK_NUMBER');
        const dayOfWeekSlot = Alexa.getSlot(handlerInput.requestEnvelope, 'DAY_OF_WEEK');
        
        let speakOutput = 'I couldn\'t find any mood details for that time.';
        
        if (weekNumberSlot && dayOfWeekSlot && weekNumberSlot.value && dayOfWeekSlot.value) {
            const weekNumber = parseInt(weekNumberSlot.value);
            const dayOfWeek = dayOfWeekSlot.value.toLowerCase(); 
            
            // Check if the details are available in session attributes
            const dailyMoodDetails = sessionAttributes.dailyMoodDetails || [];            
            const detail = dailyMoodDetails.find(d => {
                    return d.weekLabel === weekNumber &&d.dayOfWeek === dayOfWeek;
            });
            if (detail) {
                speakOutput = `On ${detail.dayOfWeek}, week ${weekNumber}, you felt ${detail.mood} with an intensity of ${detail.intensity} because you ${detail.reason}.`;
            }
        } else {
            speakOutput = 'Please make sure to provide both a week number and a day of the week.';
        }
        
        return handlerInput.responseBuilder
            .speak(speakOutput)
            .reprompt(speakOutput)
            .getResponse();
    }
};

module.exports.MoodDetailsByDayIntentHandler = MoodDetailsByDayIntentHandler;
