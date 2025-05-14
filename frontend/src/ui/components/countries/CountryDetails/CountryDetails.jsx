import React from 'react';
import {useNavigate, useParams} from "react-router";
import useCountryDetails from "../../../../hooks/useCountryDetails.js";
import {
    Box,
    Button,
    CircularProgress,
    Grid,
    Typography,
    Paper,
    // Avatar,
    Stack,
    Breadcrumbs,
    Link
} from "@mui/material";
import {
    ArrowBack,
    Share
} from "@mui/icons-material";

const CountryDetails = () => {
    const navigate = useNavigate();
    const {id} = useParams();
    const numericId = Number(id);
    const {country} = useCountryDetails(numericId);

    if (!country) {
        return (
            <Box sx={{display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh'}}>
                <CircularProgress/>
            </Box>
        );
    }

    return (
        <Box>
            <Breadcrumbs aria-label="breadcrumb" sx={{mb: 3}}>
                <Link
                    underline="hover"
                    color="inherit"
                    href="#"
                    onClick={(e) => {
                        e.preventDefault();
                        navigate("/countries");
                    }}
                >
                    Countries
                </Link>
                <Typography color="text.primary">{country.name}</Typography>
            </Breadcrumbs>

            <Paper elevation={2} sx={{p: 4, borderRadius: 4}}>
                <Grid container spacing={4}>
                    <Grid size={{xs: 12, md: 3}}>
                        <Box sx={{
                            display: 'flex',
                            justifyContent: 'center',
                            mb: 4,
                            bgcolor: 'background.paper',
                            p: 3,
                            borderRadius: 3,
                            boxShadow: 1
                        }}>
                            {/*<Avatar*/}
                            {/*    src={country.image || "/placeholder-country.jpg"}*/}
                            {/*    variant="rounded"*/}
                            {/*    sx={{*/}
                            {/*        width: '100%',*/}
                            {/*        height: 'auto',*/}
                            {/*        objectFit: 'contain'*/}
                            {/*    }}*/}
                            {/*/>*/}
                        </Box>
                    </Grid>
                    <Grid size={{xs: 12, md: 9}}>
                        <Box sx={{mb: 3}}>
                            <Typography variant="h3" gutterBottom sx={{fontWeight: 600}}>
                                {country.name}
                            </Typography>
                            <Typography variant="h3" gutterBottom sx={{fontWeight: 600}}>
                                {country.continent}
                            </Typography>

                            <Typography variant="body1" sx={{mb: 3}}>
                                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi consequatur culpa
                                doloribus, enim maiores possimus similique totam ut veniam voluptatibus. Adipisci
                                consequatur, cum dolorem eaque enim explicabo fugit harum, id laborum non totam ut!
                                Architecto assumenda deserunt doloribus ducimus labore magnam officiis sunt. Autem culpa
                                eaque obcaecati quasi, quo vitae.
                            </Typography>
                        </Box>
                    </Grid>
                    <Grid size={12} display="flex" justifyContent="space-between">
                        <Stack direction="row" spacing={2}>
                            <Button
                                variant="outlined"
                                startIcon={<Share/>}
                            >
                                Share
                            </Button>
                        </Stack>
                        <Button
                            variant="outlined"
                            startIcon={<ArrowBack/>}
                            onClick={() => navigate("/countries")}
                        >
                            Back to Countries
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </Box>
    );
};

export default CountryDetails;
