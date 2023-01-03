package abc.model;

import java.security.InvalidParameterException;


public interface LinearAlgebra {

    public class Vector {

        public double[] values;
        public int dimension;

        public Vector(double[] components) {
            this.values = components;
            this.dimension = this.values.length;
        }

        public double dot(Vector _vector) throws InvalidParameterException {

            if (_vector.dimension != this.dimension) {
                throw new InvalidParameterException("The two vectors must be of the same length.");
            }
            else {
                double result = 0;
                int length = this.values.length;

                for (int index = 0; index < length; index++) {
                    result += this.values[index] * _vector.values[index];
                }

                return result;
            }
        }

        public Vector sum(Vector _vector) {
            if (_vector.dimension != this.dimension) {
                throw new InvalidParameterException("Cannot add vectors of different dimension");
            }
            else {
                double[] result = new double[this.dimension];

                for (int index = 0; index < this.dimension; index++) {
                    result[index] = this.values[index] + _vector.values[index];
                }

                return new Vector(result);

            }
        }
    }


    public class Matrix {
        
        public Vector[] values;
        public int[] dimension;

        public Matrix (double[][] rows) throws InvalidParameterException {
            int rowDimension = rows[0].length;
            int colDimension = rows.length;

            this.values = new Vector[colDimension];

            for (int rowIndex = 0; rowIndex < colDimension; rowIndex++) {

                double[] row = rows[rowIndex];

                if (row.length != rowDimension) {
                    throw new InvalidParameterException(
                        "All rows in a matrix must be of the same length.");
                }
                else {
                    this.values[rowIndex] = new Vector(row);
                }
            }

            this.dimension = new int[] {colDimension, rowDimension};
        }

        public Vector multVec(Vector _vector) throws InvalidParameterException {
            if (this.dimension[1] != _vector.dimension) {
                throw new InvalidParameterException(
                    "The tensor dimensions disallow multiplication.");
            }
            else {
                Vector result = new Vector(new double[this.dimension[0]]);

                for (int rowIndex = 0; rowIndex < this.dimension[0]; rowIndex++ ) {
                    result.values[rowIndex] = this.values[rowIndex].dot(_vector);
                }

                return result;
            }
        }
    }
}
