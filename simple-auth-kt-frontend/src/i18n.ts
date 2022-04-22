import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

import en from './locales/en/translation.json';
import sk from './locales/sk/translation.json';

const resources = {
    en: {
        translation: en
    },
    sk: {
        translation: sk
    }
};

const storageLocale = localStorage.getItem('locale');

i18n
    .use(initReactI18next)
    .init({
        debug: true,
        lng: storageLocale ? storageLocale : 'en',
        fallbackLng: 'en',
        interpolation: {
            escapeValue: false,
        },
        resources
    });

export default i18n;
