import React, { useState } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    IconButton
} from "@mui/material";
import InfoIcon from '@mui/icons-material/Info';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import EditBookDialog from "../EditBookDialog/EditBookDialog.jsx";
import DeleteBookDialog from "../DeleteBookDialog/DeleteBookDialog.jsx";
import { useNavigate } from "react-router";

const BooksTable = ({ books, onEdit, onDelete }) => {
    const navigate = useNavigate();
    const [editBookDialogOpen, setEditBookDialogOpen] = useState(false);
    const [deleteBookDialogOpen, setDeleteBookDialogOpen] = useState(false);
    const [selectedBook, setSelectedBook] = useState(null);

    const handleEditClick = (book) => {
        setSelectedBook(book);
        setEditBookDialogOpen(true);
    };

    const handleDeleteClick = (book) => {
        setSelectedBook(book);
        setDeleteBookDialogOpen(true);
    };

    return (
        <>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Title</TableCell>
                            <TableCell>Category</TableCell>
                            <TableCell>Available Copies</TableCell>
                            <TableCell align="right">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {books && books.length > 0 ? (
                            books.map(book => (
                                <TableRow key={book.id}>
                                    <TableCell>{book.name}</TableCell>
                                    <TableCell>{book.category}</TableCell>
                                    <TableCell>{book.availableCopies}</TableCell>
                                    <TableCell align="right">
                                        <IconButton onClick={() => navigate(`/books/${book.id}`)}><InfoIcon /></IconButton>
                                        <IconButton onClick={() => handleEditClick(book)}><EditIcon /></IconButton>
                                        <IconButton onClick={() => handleDeleteClick(book)}><DeleteIcon /></IconButton>
                                    </TableCell>
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={4} align="center">
                                    No books available.
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            {selectedBook && (
                <>
                    <EditBookDialog
                        open={editBookDialogOpen}
                        onClose={() => setEditBookDialogOpen(false)}
                        book={selectedBook}
                        onEdit={onEdit}
                    />
                    <DeleteBookDialog
                        open={deleteBookDialogOpen}
                        onClose={() => setDeleteBookDialogOpen(false)}
                        book={selectedBook}
                        onDelete={onDelete}
                    />
                </>
            )}
        </>
    );
};

export default BooksTable;
