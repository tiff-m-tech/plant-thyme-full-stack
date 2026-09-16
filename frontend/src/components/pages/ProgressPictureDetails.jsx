import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBug, faTrashCan, faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";
import {
    getProgressPicture,
    updateProgressPicture,
    deleteProgressPicture,
} from "../../services/api";
import { SERVER_URL } from "../../constants";
import SectionDivider from "../ui/SectionDivider";
import Button from "../ui/Button";
import PageTitle from "../ui/PageTitle";
import Loading from "../ui/Loading";
import Modal from "../ui/Modal";

const UPDATE_TYPES = ["Growth", "Repotting", "Pruning", "Pest Treatment", "Other"];

export default function ProgressPictureDetails() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [picture, setPicture] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({ updateType: "", notes: "" });
    const [showConfirm, setShowConfirm] = useState(false);
    const [errors, setErrors] = useState([]);

    useEffect(() => {
        async function loadPicture() {
            try {
                const data = await getProgressPicture(id);
                setPicture(data);
                setFormData({
                    pictureDate: data.pictureDate,
                    updateType: data.updateType ?? "",
                    notes: data.notes ?? "",
                });
            } catch (error) {
                console.error("Failed to load progress picture:", error);
            } finally {
                setLoading(false);
            }
        }
        loadPicture();
    }, [id]);

    function handleChange(event) {
        const { name, value } = event.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    }

    async function handleSave() {
        try {
            const details = {
                imagePath: picture.imagePath,
                pictureDate: formData.pictureDate,
                updateType: formData.updateType === "" ? null : formData.updateType,
                notes: formData.notes === "" ? null : formData.notes,
            };
            await updateProgressPicture(id, details);
            const refreshed = await getProgressPicture(id);
            setPicture(refreshed);
            setErrors([]);
            setIsEditing(false);
        } catch (error) {
            setErrors(error.message ?? ["Something went wrong. Please try again."]);
        }
    }

    async function handleDelete() {
        try {
            await deleteProgressPicture(id);
            navigate(-1); // go back to the plant details page
        } catch (error) {
            console.error("Failed to delete progress picture:", error);
        }
    }

    if (loading) return <Loading />;

    if (!picture) {
        return (
            <main>
                <h1>Picture not found!</h1>
                <Button innerText="Back" onClick={() => navigate(-1)} />
            </main>
        );
    }

    return (
        <main id="progressPictureDetails">
            <Button innerText="Back" onClick={() => navigate(-1)} className="back-btn" />
            <PageTitle title={picture.collectionPlant.plant.name} />
            <img
                src={`${SERVER_URL}/uploads/progress-pictures/${picture.imagePath}`}
                alt={`Progress picture of ${picture.collectionPlant.plant.name} from ${picture.pictureDate}`}
                className="progress-picture-page-image"
            />
            <form>
                <label htmlFor="pictureDate">Photo Date:</label>
                <input
                    id="pictureDate"
                    type="date"
                    name="pictureDate"
                    value={formData.pictureDate}
                    disabled={!isEditing}
                    onChange={handleChange}
                />
                <label htmlFor="updateType">Update Type:</label>
                <select
                    id="updateType"
                    name="updateType"
                    value={formData.updateType}
                    disabled={!isEditing}
                    onChange={handleChange}
                >
                    <option value="">-- Select --</option>
                    {UPDATE_TYPES.map((type) => (
                        <option key={type} value={type}>
                            {type}
                        </option>
                    ))}
                </select>
                <label htmlFor="notes">Notes:</label>
                <textarea
                    id="notes"
                    name="notes"
                    rows="6"
                    maxLength="1000"
                    placeholder="Your notes here..."
                    value={formData.notes}
                    disabled={!isEditing}
                    onChange={handleChange}
                />
                {errors.length > 0 && (
                    <div className="form-errors">
                        <p>
                            <FontAwesomeIcon icon={faBug} /> Please fix the following:
                        </p>
                        <ul className="form-errors">
                            {errors.map((msg, index) => (
                                <li key={index}>{msg}</li>
                            ))}
                        </ul>
                    </div>
                )}
                {isEditing ? (
                    <Button innerText="Save" onClick={handleSave} />
                ) : (
                    <Button
                        innerText="Edit"
                        onClick={() => {
                            setIsEditing(true);
                            setErrors([]);
                        }}
                    />
                )}
            </form>
            <SectionDivider />
            <div className="remove-btn-container">
                <Button
                    innerText="Remove Progress Picture"
                    icon={faTrashCan}
                    onClick={() => setShowConfirm(true)}
                    className="remove-btn"
                />
            </div>
            <Modal
                role="dialog"
                aria-modal="true"
                isOpen={showConfirm}
                onClose={() => setShowConfirm(false)}
                onConfirm={handleDelete}
                message="Are you sure you want to remove this progress picture and it's details?"
                confirmText="Yes, Remove"
                cancelText="Cancel"
                iconClassName="modal-yellow-warning-icon"
                confirmButtonClassName="remove-btn"
                icon={faTriangleExclamation}
            />
        </main>
    );
}
