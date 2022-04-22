import React, { FunctionComponent } from 'react';
import { Badge, Container, Link, Stack, Text } from '@chakra-ui/react';
import { useTranslation } from 'react-i18next';
import { useQuery, } from 'react-query';

const Footer: FunctionComponent = () => {
    const {isLoading, isError} = useQuery('health', async () => {
        const result = await fetch('/api/backend/health');
        if (result.status === 200) {
            return result.text();
        } else {
            throw new Error('Something went wrong!!!');
        }
    }, {
        refetchInterval: 1000
    });

    const {t} = useTranslation();

    return (
        <footer>
            <Container maxW="container.sm" marginTop="10">
                <Stack>
                    <Text align="center">
                        <strong>{t('title')}</strong>
                        <span> {t('footer.by')} </span>
                        <Link href="https://www.janobono.com/" isExternal>janobono</Link>
                        <span>. {t('footer.license')}</span>

                        {isLoading &&
                            <React.Fragment>
                                <span> </span>
                                <Badge colorScheme="yellow">{t('footer.loading')}</Badge>
                            </React.Fragment>
                        }

                        {!isLoading && isError &&
                            <React.Fragment>
                                <span> </span>
                                <Badge colorScheme="red">{t('footer.error')}</Badge>
                            </React.Fragment>
                        }

                        {!isLoading && !isError &&
                            <React.Fragment>
                                <span> </span>
                                <Badge colorScheme="green">{t('footer.healthy')}</Badge>
                            </React.Fragment>
                        }
                    </Text>
                </Stack>
            </Container>
        </footer>
    );
}

export default Footer;
