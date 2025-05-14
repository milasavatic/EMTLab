import React from 'react';
import {useNavigate, useParams} from "react-router";
import useBookDetails from "../../../../hooks/useBookDetails.js";
import {
    Box,
    Button,
    Chip,
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
    Category,
    FavoriteBorder,
    Share,
    ShoppingCart,
} from "@mui/icons-material";

const BookDetails = () => {
    const navigate = useNavigate();
    const {id} = useParams();
    const numericId = Number(id);
    const {book, author} = useBookDetails(numericId);

    if (!book || !author) {
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
                        navigate("/books");
                    }}
                >
                    Books
                </Link>
                <Typography color="text.primary">{book.name}</Typography>
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
                            {/*    src={book.image || "/placeholder-book.jpg"}*/}
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
                                {book.name}
                            </Typography>
                            <Typography variant="h4" color="primary.main" sx={{mb: 3}}>
                                {book.category}
                            </Typography>
                            <Typography variant="subtitle1" sx={{mb: 3}}>
                                {book.availableCopies} piece(s) available
                            </Typography>

                            <Typography variant="body1" sx={{mb: 3}}>
                                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Animi consequatur culpa
                                doloribus, enim maiores possimus similique totam ut veniam voluptatibus. Adipisci
                                consequatur, cum dolorem eaque enim explicabo fugit harum, id laborum non totam ut!
                                Architecto assumenda deserunt doloribus ducimus labore magnam officiis sunt. Autem culpa
                                eaque obcaecati quasi, quo vitae.
                            </Typography>

                            <Stack direction="row" spacing={1} sx={{mb: 3}}>
                                <Chip
                                    icon={<Category/>}
                                    label={author.name}
                                    color="primary"
                                    variant="outlined"
                                    sx={{p: 2}}
                                />
                                {/*<Chip*/}
                                {/*    icon={<Category/>}*/}
                                {/*    label={author.country.name}*/}
                                {/*    color="secondary"*/}
                                {/*    variant="outlined"*/}
                                {/*    sx={{p: 2}}*/}
                                {/*/>*/}
                            </Stack>
                        </Box>
                    </Grid>
                    <Grid size={12} display="flex" justifyContent="space-between">
                        <Stack direction="row" spacing={2}>
                            <Button
                                variant="contained"
                                color="primary"
                                startIcon={<ShoppingCart/>}
                                size="large"
                            >
                                Add to WishList
                            </Button>
                            <Button
                                variant="outlined"
                                color="secondary"
                                startIcon={<FavoriteBorder/>}
                            >
                                Wishlist
                            </Button>
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
                            onClick={() => navigate("/books")}
                        >
                            Back to Books
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </Box>
    );
};

export default BookDetails;
