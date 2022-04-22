import * as React from 'react'
import { FunctionComponent, useContext } from 'react'
import { IconButton, } from '@chakra-ui/react'
import ReactCountryFlag from 'react-country-flag'
import GlobalContext from '../contexts/global-context';

const LocaleSwitcher: FunctionComponent = () => {
    const globalCtx = useContext(GlobalContext);

    return (
        <IconButton
            size="md"
            fontSize="lg"
            variant="ghost"
            color="current"
            marginLeft="2"
            onClick={() => globalCtx.setLocale(globalCtx.locale === 'en' ? 'sk' : 'en')}
            icon={globalCtx.locale === 'en' ?
                <ReactCountryFlag countryCode="SK"/> :
                <ReactCountryFlag countryCode="GB"/>
            }
            aria-label={`Switch to ${globalCtx.locale === 'en' ? 'sk' : 'en'}`}
        />
    )
}

export default LocaleSwitcher;
