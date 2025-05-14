import React, {useState} from 'react';
import InfoIcon from '@mui/icons-material/Info';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import {Box, Button, Card, CardActions, CardContent, Typography} from "@mui/material";
import EditBookDialog from "../EditBookDialog/EditBookDialog.jsx";
import DeleteBookDialog from "../DeleteBookDialog/DeleteBookDialog.jsx";
import {useNavigate} from "react-router";

const BookCard = ({book, onEdit, onDelete}) => {
    const navigate = useNavigate();
    const [editBookDialogOpen, setEditBookDialogOpen] = useState(false);
    const [deleteBookDialogOpen, setDeleteBookDialogOpen] = useState(false);

    return (
        <>
            <Card sx={{boxShadow: 3, borderRadius: 2, p: 1}}>
                <CardContent>
                    <Typography variant="h5">{book.name}</Typography>
                    <Typography variant="subtitle2">
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab aperiam assumenda blanditiis cum
                        ducimus enim modi natus odit quibusdam veritatis.
                    </Typography>
                    {/*<Typography variant="body1" fontWeight="bold"*/}
                    {/*            sx={{textAlign: "right", fontSize: "1.25rem"}}>{book.author.name}</Typography>*/}
                    <Typography variant="body1" fontWeight="bold"
                                sx={{textAlign: "right", fontSize: "1.25rem"}}>{book.category}</Typography>
                    <Typography variant="body2" sx={{textAlign: "right"}}>{book.availableCopies} piece(s)
                        available</Typography>
                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button
                        size="small"
                        color="info"
                        startIcon={<InfoIcon/>}
                        onClick={() => navigate(`/books/${book.id}`)}
                    >
                        Info
                    </Button>
                    <Box>
                        <Button
                            size="small"
                            color="warning"
                            startIcon={<EditIcon/>}
                            sx={{mr: "0.25rem"}}
                            onClick={() => setEditBookDialogOpen(true)}
                        >
                            Edit
                        </Button>
                        <Button
                            size="small"
                            color="error"
                            startIcon={<DeleteIcon/>}
                            onClick={() => setDeleteBookDialogOpen(true)}
                        >
                            Delete
                        </Button>
                    </Box>
                </CardActions>
            </Card>
            <EditBookDialog
                open={editBookDialogOpen}
                onClose={() => setEditBookDialogOpen(false)}
                book={book}
                onEdit={onEdit}
            />
            <DeleteBookDialog
                open={deleteBookDialogOpen}
                onClose={() => setDeleteBookDialogOpen(false)}
                book={book}
                onDelete={onDelete}
            />
        </>
    );
};

export default BookCard;
