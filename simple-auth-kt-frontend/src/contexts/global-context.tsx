import React, { FunctionComponent, useState } from 'react';
import i18n from '../i18n';

export interface Global {
    locale: string,
    setLocale: (locale: string) => void
}

const GlobalContext = React.createContext<Global>({
    locale: 'sk',
    setLocale: (locale: string) => {
    }
});

export const GlobalContextProvider: FunctionComponent = (props) => {
    const [locale, setLocaleState] = useState(i18n.language);

    const setLocale = (locale: string) => {
        localStorage.setItem('locale', locale);
        if (i18n.language !== locale) {
            i18n.changeLanguage(locale);
        }
        setLocaleState(locale);
    }

    return (
        <GlobalContext.Provider
            value={
                {
                    locale,
                    setLocale
                }
            }
        >{props.children}
        </GlobalContext.Provider>
    );
}

export default GlobalContext;
