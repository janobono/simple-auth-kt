import React, { FunctionComponent } from 'react';
import { AuthContextProvider } from './auth-context';
import { GlobalContextProvider } from './global-context';

const AppContext: FunctionComponent = (props) => {
    return (
        <GlobalContextProvider>
            <AuthContextProvider>
                {props.children}
            </AuthContextProvider>
        </GlobalContextProvider>
    );
}

export default AppContext;
