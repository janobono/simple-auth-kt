import React from 'react';
import ReactDOM from 'react-dom';
import { QueryClient, QueryClientProvider } from 'react-query';
import { ChakraProvider } from '@chakra-ui/react';
import App from './App';
import AppContext from './contexts/AppContext';
import '@fontsource/raleway/400.css'
import '@fontsource/open-sans/700.css'
import theme from './theme';

const queryClient = new QueryClient();

ReactDOM.render(
    <React.StrictMode>
        <QueryClientProvider client={queryClient}>
            <ChakraProvider theme={theme}>
                <AppContext>
                    <App/>
                </AppContext>
            </ChakraProvider>
        </QueryClientProvider>
    </React.StrictMode>,
    document.getElementById('root')
);
