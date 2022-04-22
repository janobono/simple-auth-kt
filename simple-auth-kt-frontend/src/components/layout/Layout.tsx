import React, { FunctionComponent } from 'react';
import Header from './Header';
import Footer from './Footer';
import { Container } from '@chakra-ui/react';

const Layout: FunctionComponent = (props) => {
    return (
        <React.Fragment>
            <Header/>
            <Container maxW="container.sm" marginBottom="10">
                {props.children}
            </Container>
            <Footer/>
        </React.Fragment>
    );
}

export default Layout;
