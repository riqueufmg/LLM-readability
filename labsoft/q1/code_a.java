public static char getClassTypeCharForPrimitiveTypeString(String type)
	{
		switch (type)
		{
		case S_TYPE_NAME_INTEGER:
			return TYPE_INTEGER;
		case S_TYPE_NAME_BOOLEAN:
			return TYPE_BOOLEAN;
		case S_TYPE_NAME_LONG:
			return TYPE_LONG;
		case S_TYPE_NAME_DOUBLE:
			return TYPE_DOUBLE;
		case S_TYPE_NAME_FLOAT:
			return TYPE_FLOAT;
		case S_TYPE_NAME_SHORT:
			return TYPE_SHORT;
		case S_TYPE_NAME_BYTE:
			return TYPE_BYTE;
		case S_TYPE_NAME_CHARACTER:
			return TYPE_CHARACTER;
		case S_TYPE_NAME_VOID:
			return TYPE_VOID;
		}

		throw new RuntimeException(type + " is not a primitive type");
	}