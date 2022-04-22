import { FunctionComponent, useContext, useState } from 'react';
import { FieldValues, useForm } from 'react-hook-form'
import {
    Alert,
    AlertIcon,
    AlertTitle,
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    Input,
} from '@chakra-ui/react'
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router';
import { useMutation } from 'react-query';
import AuthContext from '../contexts/auth-context';

const LogInPage: FunctionComponent = () => {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const authCtx = useContext(AuthContext);

    const [error, setError] = useState(false);

    const {
        handleSubmit,
        register,
        formState: {errors, isSubmitting},
    } = useForm();

    const {mutate} = useMutation(authCtx.onLogin, {
        onSuccess: data => {
            navigate('/');
        },
        onError: () => {
            setError(true);
        }
    });

    const onSubmit = async (values: FieldValues) => {
        setError(false);
        mutate({username: values.username, password: values.password});
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <FormControl isInvalid={errors.username}>
                <FormLabel htmlFor="username">{t('logIn.username.label')}</FormLabel>
                <Input
                    type="text"
                    id="username"
                    placeholder={t('logIn.username.label')}
                    {...register('username', {
                        required: t('logIn.username.required').trim()
                    })}
                />
                <FormErrorMessage>
                    {errors.username && errors.username.message}
                </FormErrorMessage>
            </FormControl>

            <FormControl isInvalid={errors.password}>
                <FormLabel htmlFor="password">{t('logIn.password.label')}</FormLabel>
                <Input
                    type="password"
                    id="password"
                    placeholder={t('logIn.password.label')}
                    {...register('password', {
                        required: t('logIn.password.required').trim()
                    })}
                />
                <FormErrorMessage>
                    {errors.password && errors.password.message}
                </FormErrorMessage>
            </FormControl>

            <Button mt={4} isLoading={isSubmitting} type="submit">
                {t('logIn.submit')}
            </Button>

            {error &&
                <Alert status="error">
                    <AlertIcon/>
                    <AlertTitle mr={2}>{t('logIn.error')}</AlertTitle>
                </Alert>
            }
        </form>
    );
}

export default LogInPage;
