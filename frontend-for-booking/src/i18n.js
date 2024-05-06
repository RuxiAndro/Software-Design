// i18n.js
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

// Inițializarea i18next
i18n.use(initReactI18next).init({
  resources: {
    en: {
      translation: {
        greeting: 'Hello! Welcome to our website!',
        'lang.change': 'Change the language',
        'lang.eng': 'English',
        'lang.fr': 'French',
      },
    },
    fr: {
      translation: {
        greeting: 'Bonjour! Bienvenue sur notre site!',
        'lang.change': 'Changez la langue',
        'lang.eng': 'Anglais',
        'lang.fr': 'Français',
      },
    },
  },
  lng: 'en', // Limba implicită
  fallbackLng: 'en', // Limba implicită de rezervă
});

export default i18n;
